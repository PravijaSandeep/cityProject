 function toggleOtherTopicInput() {
	 		console.log("toggleOtherTopicInput called");
            var selectElement = document.getElementById("topicName");
            var otherTopicInput = document.getElementById("otherTopicInput");

            if (selectElement.value === "Other") {
                otherTopicInput.style.display = "block";
            } else {
                otherTopicInput.style.display = "none";
            }
        }