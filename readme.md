# Smart Security System



## The issues being addressed:



* **Personal Security** : Sending out distress calls at critical moments. 

* **Home Security**: Monitoring home security on-the-go.


## Our Proposal

An interface (app) on our mobile phone featuring:

### Personal Security:

* Add and Save Contacts.

* Retrieve and Send Live Tracking of our Location along with a distress message to the contacts.

* In case of an emergency, the user can press the distress button on the app to send pre-saved messages to pre-saved contacts along with the live location tracking link.

### Home Security:
* Interact with a motion sensor, a camera and a password verification system installed at home.

* In case of motion in the house with the door being locked, a notification is sent to the mobile and an high-decibel alarm rings in the house.

* After a person enters the house using the key itself, he/she should verify the entry by typing a password in the password verification system installed near the door within 15s. If not done, a notification is sent to the mobile.

* User can view the history of entries in the house in the app.

## APIs used

* **Google Maps API** for live tracking.
* **SMS Sending API** to send SMS to pre-saved contacts.
* **Web API (using Node)** to transfer data online between password verification system and the mobile.
* **Generic Sensor API** for various sensors.
* **Motion Sensor API** for infra-red motion sensing.