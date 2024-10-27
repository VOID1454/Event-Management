// Fetch Events from Backend
function fetchEvents() {
    fetch('/api/events')
        .then(response => response.json())
        .then(events => {
            const eventsList = document.getElementById('eventsList');
            eventsList.innerHTML = ''; // Clear existing events
            events.forEach(event => {
                const li = document.createElement('li');
                li.innerHTML = `<strong>${event.name}:</strong> ${event.startDate} to ${event.endDate}`;
                eventsList.appendChild(li);
            });
        })
        .catch(error => console.error('Error fetching events:', error));
}

// Event Filtering Functionality
function filterEvents() {
    const filterInput = document.getElementById('eventFilter').value.toLowerCase();
    const eventsList = document.getElementById('eventsList');
    const eventsItems = eventsList.getElementsByTagName('li');

    for (let i = 0; i < eventsItems.length; i++) {
        const itemText = eventsItems[i].innerText.toLowerCase();
        eventsItems[i].style.display = itemText.includes(filterInput) ? '' : 'none';
    }
}

// Slideshow Functionality
let currentSlide = 0;
const slides = document.querySelectorAll('.slide');

function showSlide() {
    slides.forEach((slide, index) => {
        slide.classList.toggle('active', index === currentSlide);
    });
    currentSlide = (currentSlide + 1) % slides.length;
}

setInterval(showSlide, 4000); // Change slide every 4 seconds

// Initial Fetch
fetchEvents();
