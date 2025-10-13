# TODO app

The TODO mobile app helps people stay organized by keeping track of all the things they need to do in one simple place. Instead of writing tasks in a notebook or forgetting them, users can quickly add reminders, categorize their tasks (like work, personal, or shopping), and mark them as done when completed. The app also lets users set priorities and deadlines, making it easier to focus on what matters most. Even when users are offline, they can still access and manage their notes — everything will sync automatically once they reconnect to the internet.
---

### Entities

The app stores information about Notes. Each note represents a single task or reminder that the user creates.

| Field           | Type     | Description                                                                      |
| --------------- | -------- | -------------------------------------------------------------------------------- |
| **id**          | Integer  | A unique number used internally to identify each note.                           |
| **name**        | Text     | The title of the note — a short phrase describing what the task is about.        |
| **description** | Text     | Additional details or context about the task.                                    |
| **priority**    | Integer  | Indicates how urgent or important the task is (1 = low priority, 5 = very high). |
| **category**    | Text     | Helps group notes into categories like "Work", "Personal", or "Shopping".        |
| **deadline**    | DateTime | Optional field showing when the task should be completed.                        |


### CRUD operations

READ -> a user can read his notes

CREATE -> a user can create notes by filling in a form with all its required fields

UPDATE -> a user can tap his finger on a note and more details will be shown, where the option to update details of it (except id) will be.

DELETE -> a user can tap his finger on a note and more details will be shown, where there will be a delete button next to the update one.


### Persistence

Every CRUD operation will be persisted on the server. Local storage will only come into play if the device is offline (there will be a cache for that).

### Offline access

In the case that the device is offline, local storage will be implemented such that any crud operations will be saved locally in a cache, which will periodically try to upload everything in the hopes that the device is back online at some point. This will continue until we have received internet connection.

If I create a note it is saved locally in the cache, the same for update. For delete we will only store ids locally not the entire note, and then for read we will read the last cache of notes. So not the actual notes on the server but something that was recently fetched when we were online.

### Design

[Link will be here soon](link)
