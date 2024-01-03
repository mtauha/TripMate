# TripMate - Your AI Travel Guide

**TripMate** is a comprehensive travel planning app designed to streamline the entire journey experience. Whether you're an individual traveler or part of a group, TripMate offers an array of features to make trip planning and execution a breeze.

## Key Features

### 1. Personalized Itineraries

Create and manage personalized itineraries effortlessly. Optimize routes using the Google Maps API for a smooth and efficient travel experience.

### 2. Social Collaboration

TripMate introduces a social dimension to travel planning. Users can form groups, collaboratively plan trips, and engage in real-time group chats.

### 3. Media Sharing

Capture and share the best moments of your journey with ease. TripMate provides a dedicated gallery for photos and videos collected during the trip.

### 4. Privacy Controls

Tailor your profile and content visibility with robust privacy controls. Ensure that your travel experiences are shared only with the audience you choose.

### 5. Security Prioritization

TripMate prioritizes security with robust authentication and data encryption, ensuring that your travel information remains private and secure.

### 6. Notifications

Stay updated on group activities and messages with real-time notifications. TripMate keeps you in the loop, enhancing communication within the travel group.

### 7. Intuitive Interface

Enjoy an intuitive and user-friendly interface that makes trip planning a seamless experience. TripMate is designed for ease of use across various devices.

### 8. Scalability

TripMate is scalable, catering to both individual travelers and larger groups. Its flexibility ensures an enhanced user experience regardless of the scale of your journey.

## Project Configuration

1. Create the **tripdb** database by running the `Database_Queries/Schema.sql` file in your preferred SQL editor, such as MySQL Workbench.
2. Update your database connection in `APIs/connection.php` by adding the host IP address, username, password, and database name.
3. Now update your host IP address at `app/src/main/res/xml/network_security_config.xml` file.
4. And Lastly update IP address at `app\src\main\java\com\project\tripmate\mainapp\CommonFunctions.kt` file.

## Get Started

1. Clone the repository to your local machine.
2. Follow the project configuration steps mentioned above.
3. Run the application and start planning your next adventure with TripMate!

TripMate is your ideal companion for dynamic and efficient travel planning. Download it today and explore the world hassle-free!

*Note: Make sure you have MySQL server installed on your device before configuring the project.*
