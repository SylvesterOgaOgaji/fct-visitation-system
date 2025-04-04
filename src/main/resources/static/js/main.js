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
    const facilitySelect = document.getElementById('facility');
    const officerSelect = document.getElementById('officer');
    const purposeSelect = document.getElementById('purposeOfVisit');
    
    if (facilitySelect && officerSelect) {
        facilitySelect.addEventListener('change', function() {
            const facilityId = this.value;
            if (facilityId) {
                fetch(`/api/facilities/${facilityId}/officers`)
                    .then(response => response.json())
                    .then(data => {
                        officerSelect.innerHTML = '<option value="">Select Officer</option>';
                        data.forEach(officer => {
                            const option = document.createElement('option');
                            option.value = officer.officerId;
                            option.textContent = `${officer.name} - ${officer.position} (${officer.department})`;
                            officerSelect.appendChild(option);
                        });
                    });
                    
                fetch(`/api/facilities/${facilityId}/purposes`)
                    .then(response => response.json())
                    .then(data => {
                        purposeSelect.innerHTML = '<option value="">Select Purpose</option>';
                        data.forEach(purpose => {
                            const option = document.createElement('option');
                            option.value = purpose.purposeId;
                            option.textContent = purpose.description;
                            purposeSelect.appendChild(option);
                        });
                    });
            }
        });
    }
});
