# TODO app

The TODO mobile app helps people stay organized by keeping track of all the things they need to do in one simple place. Instead of writing tasks in a notebook or forgetting them, users can quickly add reminders, categorize their tasks (like work, personal, or shopping), and mark them as done when completed. The app also lets users set priorities and deadlines, making it easier to focus on what matters most. Even when users are offline, they can still access and manage their notes — everything will sync automatically once they reconnect to the internet.

---

### Entities

The app stores information about Notes. Each note represents a single task or reminder that the user creates.

| Field           | Type     | Description                                                                      |
| --------------- | -------- | -------------------------------------------------------------------------------- |
| **id**          | Integer  | A unique number used internally to identify each note.                           |
| **name**        | Text     | The title of the note — a short phrase describing what the task is about.        |
| **description** | Text     | Optional details or context about the task.                                    |
| **priority**    | Integer  | Optional indicator on how urgent or important the task is (1 = low priority, 5 = very high). |
| **category**    | Text     | Optional help for grouping notes into categories like "Work", "Personal", or "Shopping".        |
| **deadline**    | DateTime | Optional field showing when the task should be completed.                        |


### CRUD operations

**Create**<br>
When the user wants to add a new task, they open the “Add Note” screen and fill in details like the name, description, priority, and category. Once they press “Save,” the app creates the note and displays it in the main list.

**Read**<br>
When the user opens the app, they can see all their notes in a list, sorted or filtered by priority or category. They can tap a note to view its full details.

**Update**<br>
If something changes — for example, the deadline or priority — the user can tap on a note, make changes in the “Edit Note” screen, and press “Update.” The app will save the new information immediately.

**Delete**<br>
If a note is no longer needed, the user can open its details and press “Delete.” The note will be removed from the list after confirmation.

### Persistence

The app uses both a local database (on the phone) and a remote server (cloud) to store notes.
- Local Database:
  Stores notes temporarily when the device is offline or when syncing.
  Create, Read, and Update operations are stored locally first.
  Delete stores only the note ID (to remove it later on the server).
- Server:
  Stores all permanent data for each user.
  When online, Create, Read, Update, and Delete operations are immediately sent to the server.
  The local database synchronizes with the server periodically to keep data consistent.

### Offline access scenarios

When the device is offline, the app continues to function normally using the local cache. The actions are stored locally and synchronized automatically once the device reconnects.

Create (Offline): <br>
A user adds a new note while traveling with no internet. The note is saved in the local cache and marked as “Pending Sync.” Once internet is restored, the app uploads the new note to the server.

Read (Offline): <br>
A user opens the app offline. The app shows the last known list of notes (from the local database), so the user can still review existing tasks.

Update (Offline): <br>
A user edits a note’s description or deadline while offline. The changes are saved locally and flagged for synchronization. When the device reconnects, these updates overwrite the corresponding note on the server.

Delete (Offline):<br>
A user deletes a note while offline. The note’s ID is stored locally in a “to delete” list. When the app reconnects, it deletes that note on the server as well.

### Design

[https://www.figma.com/design/88XHfOLD0gHGZ2qC3kMv0F/MA?node-id=3-256&t=QYFUPj1pHI2bVZle-0](https://www.figma.com/design/88XHfOLD0gHGZ2qC3kMv0F/MA?node-id=3-256&t=QYFUPj1pHI2bVZle-0)
