// Form validation
(function () {
    'use strict'
    
    // Fetch all forms that need validation
    var forms = document.querySelectorAll('.needs-validation')
    
    // Loop over them and prevent submission
    Array.prototype.slice.call(forms)
        .forEach(function (form) {
            form.addEventListener('submit', function (event) {
                if (!form.checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                }
                form.classList.add('was-validated')
            }, false)
        })
})()

// Dynamic loading of officers based on facility selection
document.addEventListener('DOMContentLoaded', function() {
    console.log("DOM fully loaded");
    
    // Find the select elements by their ID
    const facilitySelect = document.getElementById('facility');
    const officerSelect = document.getElementById('officer');
    const purposeSelect = document.getElementById('purposeOfVisit');
    const dateTimeInput = document.getElementById('appointmentDateTime');
    
    // Configure date/time field for custom format
    if (dateTimeInput) {
        // Add event listener to reformat date on change
        dateTimeInput.addEventListener('change', function(e) {
            const dateValue = e.target.value; // This is in ISO format: YYYY-MM-DDTHH:MM
            if (dateValue) {
                // Create a date object from the ISO string
                const date = new Date(dateValue);
                
                // Format the date as M/D/YY, h:MM AM/PM
                const formattedMonth = (date.getMonth() + 1);
                const formattedDay = date.getDate();
                const formattedYear = date.getFullYear().toString().substr(-2);
                const hours = date.getHours();
                const minutes = date.getMinutes();
                const ampm = hours >= 12 ? 'PM' : 'AM';
                const formattedHours = hours % 12 || 12; // Convert to 12-hour format
                
                const formattedDate = `${formattedMonth}/${formattedDay}/${formattedYear}, ${formattedHours}:${minutes.toString().padStart(2, '0')} ${ampm}`;
                
                // Store the formatted date in a hidden field
                const hiddenField = document.getElementById('formattedDateTime');
                if (!hiddenField) {
                    const hidden = document.createElement('input');
                    hidden.type = 'hidden';
                    hidden.id = 'formattedDateTime';
                    hidden.name = 'formattedAppointmentDateTime';
                    hidden.value = formattedDate;
                    dateTimeInput.parentNode.appendChild(hidden);
                } else {
                    hiddenField.value = formattedDate;
                }
                
                console.log('Formatted date:', formattedDate);
            }
        });
    }
    
    console.log('Selected elements:');
    console.log('- Facility select:', facilitySelect);
    console.log('- Officer select:', officerSelect);
    console.log('- Purpose select:', purposeSelect);
    console.log('- DateTime input:', dateTimeInput);
    
    if (facilitySelect && officerSelect && purposeSelect) {
        console.log('All select elements found, adding change listener');
        
        facilitySelect.addEventListener('change', function() {
            const facilityId = this.value;
            console.log('Facility selected:', facilityId);
            
            if (facilityId) {
                // Fix URLs - remove any ".lities" and don't add a colon
                const officersUrl = `/fct-visitation/api/facilities/${facilityId}/officers`;
                console.log('Fetching officers from:', officersUrl);
                
                fetch(officersUrl)
                    .then(response => {
                        console.log('Officers API response status:', response.status);
                        if (!response.ok) {
                            throw new Error(`HTTP error! Status: ${response.status}`);
                        }
                        return response.json();
                    })
                    .then(data => {
                        console.log('Officers data received:', data);
                        officerSelect.innerHTML = '<option value="">Select Officer</option>';
                        data.forEach(officer => {
                            const option = document.createElement('option');
                            option.value = officer.officerId;
                            option.textContent = `${officer.name} - ${officer.position} (${officer.department})`;
                            officerSelect.appendChild(option);
                        });
                    })
                    .catch(error => {
                        console.error('Error loading officers:', error);
                    });
                
                // Same fix for purposes URL
                const purposesUrl = `/fct-visitation/api/facilities/${facilityId}/purposes`;
                console.log('Fetching purposes from:', purposesUrl);
                
                fetch(purposesUrl)
                    .then(response => {
                        console.log('Purposes API response status:', response.status);
                        if (!response.ok) {
                            throw new Error(`HTTP error! Status: ${response.status}`);
                        }
                        return response.json();
                    })
                    .then(data => {
                        console.log('Purposes data received:', data);
                        purposeSelect.innerHTML = '<option value="">Select Purpose</option>';
                        data.forEach(purpose => {
                            const option = document.createElement('option');
                            option.value = purpose.purposeId;
                            option.textContent = purpose.description;
                            purposeSelect.appendChild(option);
                        });
                    })
                    .catch(error => {
                        console.error('Error loading purposes:', error);
                    });
            }
        });
    } else {
        console.error('One or more select elements not found!');
    }
});

// Helper to format dates in display
function formatDisplayDate(dateString) {
    if (!dateString) return '';
    const date = new Date(dateString);
    return date.toLocaleDateString('en-US', { 
        month: 'numeric', 
        day: 'numeric',
        year: '2-digit',
        hour: 'numeric', 
        minute: '2-digit'
    });
}