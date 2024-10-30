Android Tablet Kiosk Mode Application
Overview
This Android application is designed to operate in Kiosk Mode on a tablet, ensuring limited user interaction with the device outside of the app's functionality. The app consists of two primary screens:

1. Login Screen: Allows users to authenticate via email and password.
2. Data Visualization Screen: Displays data retrieved from an API, manages user-device interaction based on API response, and includes QR code generation.

How It Works
1. On the login screen, the user inputs their email and password, and the app sends a POST request to authenticate the user.
2. If authentication is successful, the app transitions to the data visualization screen and sends a GET request to retrieve additional data.
3. The app monitors the is_blocked flag from the API response in real-time. Depending on its value, the app enables or disables interactions with the device.
4. The app can display a dynamically generated QR code if the qr field is present in the API response.

![Demo Animation]([https://imgur.com/a/cw0xRlQ])
