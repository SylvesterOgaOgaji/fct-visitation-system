// FCT Visitation System Main JavaScript

// Form validation
(function () {
    'use strict'
    
    // Fetch all forms that need validation
    var forms = document.querySelectorAll('.needs-validation');
    
    // Loop over them and prevent submission
    Array.prototype.slice.call(forms)
        .forEach(function (form) {
            form.addEventListener('submit', function (event) {
                if (!form.checkValidity()) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                form.classList.add('was-validated');
            }, false);
        });
})();

// Dynamic loading of officers and purposes based on facility selection
document.addEventListener('DOMContentLoaded', function() {
    console.log("DOM fully loaded");
    
    // Find the facility select element
    const facilitySelect = document.getElementById('facility');
    
    if (facilitySelect) {
        console.log("Facility select found, adding change listener");
        
        // Find the officer and purpose select elements
        const officerSelect = document.getElementById('officer');
        const purposeSelect = document.getElementById('purposeOfVisit');
        
        facilitySelect.addEventListener('change', function() {
            const facilityId = this.value;
            console.log('Facility selected:', facilityId);
            
            if (facilityId && officerSelect) {
                // Fetch officers for the selected facility
                fetch(`/fct-visitation/api/facilities/${facilityId}/officers`)
                    .then(response => {
                        console.log('Officer API response status:', response.status);
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
            }
            
            if (facilityId && purposeSelect) {
                // Fetch purposes for the selected facility
                fetch(`/fct-visitation/api/facilities/${facilityId}/purposes`)
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
    }
    
    // Handle date/time formatting
    const appointmentDateTimeInput = document.getElementById('appointmentDateTime');
    const dateInput = document.getElementById('appointmentDate');
    const timeInput = document.getElementById('appointmentTime');
    
    if (dateInput && timeInput && appointmentDateTimeInput) {
        console.log("Date/time inputs found, setting up event listeners");
        
        // Function to update the hidden datetime field
        function updateDateTime() {
            if (dateInput.value && timeInput.value) {
                const date = new Date(dateInput.value + 'T' + timeInput.value);
                appointmentDateTimeInput.value = date.toISOString();
                console.log('Updated appointmentDateTime value:', appointmentDateTimeInput.value);
            }
        }
        
        // Set up event listeners
        dateInput.addEventListener('change', updateDateTime);
        timeInput.addEventListener('change', updateDateTime);
        
        // Initialize with current date and time
        if (!dateInput.value) {
            const now = new Date();
            const tomorrow = new Date(now);
            tomorrow.setDate(tomorrow.getDate() + 1);
            
            // Format as YYYY-MM-DD for date input
            const year = tomorrow.getFullYear();
            const month = String(tomorrow.getMonth() + 1).padStart(2, '0');
            const day = String(tomorrow.getDate()).padStart(2, '0');
            dateInput.value = `${year}-${month}-${day}`;
            
            // Set default time to 9:00 AM
            timeInput.value = '09:00';
            
            // Trigger update of the hidden field
            updateDateTime();
        }
    }
    
    // Vehicle registration form handling
    const vehicleForm = document.getElementById('vehicleRegistrationForm');
    if (vehicleForm) {
        console.log("Vehicle registration form found");
        
        // Handle form submission
        vehicleForm.addEventListener('submit', function(event) {
            // Add any special handling before form submission
            console.log("Vehicle form submission initiated");
        });
    }
});