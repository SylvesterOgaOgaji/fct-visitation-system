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

// Date formatting and dynamic loading
document.addEventListener('DOMContentLoaded', function() {
    console.log("DOM fully loaded");
    
    // Find the elements
    const facilitySelect = document.getElementById('facility');
    const officerSelect = document.getElementById('officer');
    const purposeSelect = document.getElementById('purposeOfVisit');
    const dateInput = document.getElementById('appointmentDate');
    const timeInput = document.getElementById('appointmentTime');
    const datetimeInput = document.getElementById('appointmentDateTime');
    
    // Function to update the hidden datetime field
    function updateDateTime() {
        if (dateInput.value && timeInput.value) {
            const date = new Date(dateInput.value + 'T' + timeInput.value);
            
            // Format as M/d/yy, h:mm AM/PM
            const month = date.getMonth() + 1;
            const day = date.getDate();
            const year = date.getFullYear().toString().slice(-2);
            const hours = date.getHours();
            const minutes = date.getMinutes().toString().padStart(2, '0');
            const ampm = hours >= 12 ? 'PM' : 'AM';
            const hour12 = hours % 12 || 12;
            
            const formattedDate = `${month}/${day}/${year}, ${hour12}:${minutes} ${ampm}`;
            datetimeInput.value = formattedDate;
            console.log('Formatted date set to:', formattedDate);
        }
    }
    
    // Update datetime on change
    if (dateInput && timeInput && datetimeInput) {
        dateInput.addEventListener('change', updateDateTime);
        timeInput.addEventListener('change', updateDateTime);
        
        // Initialize form with current datetime
        const now = new Date();
        dateInput.value = now.toISOString().split('T')[0];
        timeInput.value = now.toTimeString().slice(0, 5);
        updateDateTime();
    }
    
    console.log('Selected elements:');
    console.log('- Facility select:', facilitySelect);
    console.log('- Officer select:', officerSelect);
    console.log('- Purpose select:', purposeSelect);
    console.log('- Date input:', dateInput);
    console.log('- Time input:', timeInput);
    console.log('- DateTime hidden field:', datetimeInput);
    
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