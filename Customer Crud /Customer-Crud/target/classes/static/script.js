document.addEventListener("DOMContentLoaded", function () {
    fetchCustomerList();
});

function fetchCustomerList() {
    fetch('http://localhost:8080/api/v1/customer/get_customer_list')
        .then(response => response.json())
        .then(customers => {
            displayCustomerList(customers);
        })
        .catch(error => console.error('Error fetching customer list:', error));
}

function displayCustomerList(customers) {
    var tableBody = document.querySelector("#customerTable tbody");
    tableBody.innerHTML = ''; // Clear previous content

    if (customers.length === 0) {
        tableBody.innerHTML = '<tr><td colspan="6">No customers found.</td></tr>';
        return;
    }

    for (var i = 0; i < customers.length; i++) {
        var customer = customers[i];
        var row = tableBody.insertRow(i);

        var cellId = row.insertCell(0);
        cellId.textContent = customer.customerId;

        var cellFirstName = row.insertCell(1);
        cellFirstName.textContent = customer.first_name;

        var cellLastName = row.insertCell(2);
        cellLastName.textContent = customer.last_name;

        var cellEmail = row.insertCell(3);
        cellEmail.textContent = customer.email;

        var cellPassword = row.insertCell(4);
        cellPassword.textContent = customer.password;

        var cellPhone = row.insertCell(5);
        cellPhone.textContent = customer.phone;

        var cellAddress = row.insertCell(6);
        cellAddress.textContent = customer.address;

        var cellStreet = row.insertCell(7);
        cellStreet.textContent = customer.street;

        var cellCity = row.insertCell(8);
        cellCity.textContent = customer.city;

        var cellState = row.insertCell(9);
        cellState.textContent = customer.state;

        var cellActions = row.insertCell(10);
        var deleteButton = createActionButton('Delete', () => deleteCustomer(customer.customerId));
        var editButton = createActionButton('Edit', () => editCustomer(customer.customerId));
        cellActions.appendChild(deleteButton);
        cellActions.appendChild(editButton);
    }
}

function createActionButton(text, onClick) {
    var button = document.createElement('button');
    button.textContent = text;
    button.addEventListener('click', onClick);
    return button;
}

function deleteCustomer(customerId) {
    if (confirm('Are you sure you want to delete this customer?')) {
        fetch(`http://localhost:8080/api/v1/customer/delete/${customerId}`, {
            method: 'DELETE',
        })
        .then(response => {
            if (response.ok) {
                alert('Customer deleted successfully!');
                fetchCustomerList(); // Refresh the customer list after deletion
            } else {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
        })
        .catch(error => {
            console.error('Error deleting customer:', error);
            alert('Error deleting customer. Please try again.');
        });
    }
}



function submitForm() {
    var firstName = document.getElementById("first-name").value;
    var lastName = document.getElementById("last-name").value;
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
     var phone = document.getElementById("phone").value;
    var address = document.getElementById("address").value;
    var street = document.getElementById("street").value;
    var city = document.getElementById("city").value;
    var state = document.getElementById("state").value;

    var user = {
        first_name: firstName, // Make sure the keys match the server's expected format
        last_name: lastName,
        email: email,
        password: password,
        phone: phone,
        address: address,
        street: street,
        city: city,
        state: state
    };

    fetch('http://localhost:8080/api/v1/customer/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        alert('User registered successfully!\nUser ID: ' + data.customerId);
        document.getElementById("registrationForm").reset();
    })
    .catch(error => {
        console.error('Error registering user:', error);
        alert('Error registering user. Please try again.');
    });
}


// Add this variable to store the ID of the customer being edited
let editingCustomerId;

function editCustomer(customerId) {
    editingCustomerId = customerId;
    fetch(`http://localhost:8080/api/v1/customer/get/${customerId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json();
        })
        .then(customer => {
            // Populate the form fields with the existing data
            document.getElementById("edit-first-name").value = customer.first_name;
            document.getElementById("edit-last-name").value = customer.last_name;
             document.getElementById("edit-email").value = customer.email;
             document.getElementById("edit-password").value = customer.email;
              document.getElementById("edit-phone").value = customer.phone;
              document.getElementById("edit-address").value = customer.address;
            document.getElementById("edit-street").value = customer.street;
            document.getElementById("edit-city").value = customer.city;
            document.getElementById("edit-state").value = customer.state;


            // Show the editPopup
            document.getElementById("editPopup").style.display = "block";
        })
        .catch(error => {
            console.error('Error fetching customer data for editing:', error);
            alert('Error fetching customer data for editing. Please try again.');
        });
}

function showEditPopup() {
    // Display the editPopup

  document.getElementById("editPopup").style.display = "block";
}

function closeEditPopup() {
    // Close the editPopup
    document.getElementById("editPopup").style.display = "none";
}

function submitUpdatedData() {
    var updatedFirstName = document.getElementById("edit-first-name").value;
    var updatedLastName = document.getElementById("edit-last-name").value;
    var updatedEmail = document.getElementById("edit-email").value;
     var updatedAddress = document.getElementById("edit-password").value;
    var updatedPhone = document.getElementById("edit-phone").value;
    var updatedAddress = document.getElementById("edit-address").value;
    var updatedStreet = document.getElementById("edit-street").value;
    var updatedCity = document.getElementById("edit-city").value;
    var updatedState = document.getElementById("edit-state").value;


    var updatedUser = {
        first_name: updatedFirstName,
        last_name: updatedLastName,
        email: updatedEmail,
        password: updatePassword,
        phone: updatedPhone,
        address: updatedAddress,
        street: updatedStreet,
        city: updatedCity,
        state: updatedState,
    };

    fetch(`http://localhost:8080/api/v1/customer/update/${editingCustomerId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(updatedUser)
    })
    .then(response => {
        if (response.ok) {
            alert('Customer updated successfully!');
            fetchCustomerList();
            closeEditPopup(); // Close the popup after updating
        } else {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
    })
    .catch(error => {
        console.error('Error updating customer:', error);
        alert('Error updating customer. Please try again.');
    });
}
