// JavaScript code for filtering options based on search input (optional)
const searchInput = document.querySelector('.search-input');
const options = document.querySelectorAll('.multiselect-options label');

searchInput.addEventListener('input', function() {
	const searchValue = this.value.toLowerCase();
	options.forEach(option => {
		const text = option.textContent.toLowerCase();
		if (text.includes(searchValue)) {
			option.style.display = 'block';
		} else {
			option.style.display = 'none';
		}
	});
});

document.addEventListener('DOMContentLoaded', function() {
	const checkboxes = document.querySelectorAll('.multiselect-options input[type="checkbox"]');
	const selectedChoices = document.getElementById('selectedChoices');
	const maxSelections = 5;

	checkboxes.forEach(checkbox => {
		checkbox.addEventListener('change', function() {
			let count = countSelectedCheckboxes();
			console.log("count :" + count);
			if (count >= maxSelections) {
				this.checked = false; // Uncheck the checkbox if the limit is exceeded
			}
			updateSelectedChoices(checkbox);
		});
	});

	function countSelectedCheckboxes() {
		return selectedChoices.querySelectorAll('li').length;
	}

	function updateSelectedChoices(checkbox) {

		const existingChoices = Array.from(selectedChoices.querySelectorAll('li')).map(li => {
			// Extract only the share label from the list item text content
			return li.textContent.trim().replace('Remove', '').trim();
		});
		const label = checkbox.parentElement.textContent.trim();
		if (checkbox.checked && countSelectedCheckboxes() < maxSelections) {
			const label = checkbox.parentElement.textContent.trim();
			console.log("Label :" + label);
			console.log("Existing choices " + existingChoices);
			if (!existingChoices.includes(label)) {
				const listItem = document.createElement('li');
				listItem.textContent = label;

				// Add a "Remove" button to the selected choice
				const removeButton = document.createElement('button');
				removeButton.textContent = 'Remove';
				removeButton.setAttribute('type', 'button');
				removeButton.setAttribute('class', 'remove-btn');
				removeButton.setAttribute('value', label); // Set the value as the label
				listItem.appendChild(removeButton);

				selectedChoices.appendChild(listItem);
			}
		}else if(!checkbox.checked){
			// fill up logic to remove the unselected item from the selectedChoices 
			 const listItemToRemove = Array.from(selectedChoices.querySelectorAll('li')).find(li => li.textContent.trim().replace('Remove', '').trim().includes(label));
            if (listItemToRemove) {
                listItemToRemove.remove();
            }
		}
	}

	

	// Event listener for removing selected items
	selectedChoices.addEventListener('click', function(event) {
		if (event.target.classList.contains('remove-btn')) {
			event.target.parentElement.remove(); // Remove the selected choice from the list
		}
	});




	const removeButtons = document.querySelectorAll('.remove-btn');

	removeButtons.forEach(button => {
		button.addEventListener('click', function() {
			this.parentElement.remove(); // Removes the list item from the UI
		});
	});


    const submitButton = document.querySelector('#sharesForm button[type="submit"]');

    // Function to update hidden input with selected choices
    function updateHiddenInput() {
		 console.log("Submit called. Getting the selection list : " );
		const selectedChoicList = document.getElementById('selectedChoices');
		const existingChoices = Array.from(selectedChoicList.querySelectorAll('li')).map(li => {
			// Extract only the share label from the list item text content
			return li.textContent.trim().replace('Remove', '').trim();
		});
		
        document.getElementById('selectedShares').value = existingChoices;
        console.log("List to send to controller : " + existingChoices);
    }

    // Event listener for form submission
    submitButton.addEventListener('click', function(event) {
		console.log("Added event listner");
        updateHiddenInput(); // Update hidden input before form submission
    });

});




