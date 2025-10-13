# TODO app

Simple TODO app that lets users write the stuff they need to do to replace the manual notebooks. It will help you write stuff down on the fly without having to stop and pick up your notebook and start writing. It lets you customize your todos in categories, how urgent they are, sorting, filtering etc.

---

### Entities

Notes:
Each note has an:
- id, int, primary key
- name, text, not null
- description, text, not null
- priority, int, not null (higher number higher priority)
- category, text, not null (defaults to "other")
- deadline, datetime, nullable

### CRUD operations

READ -> a user can read his notes

CREATE -> a user can create notes by filling in a form with all its required fields

UPDATE -> a user can tap his finger on a note and more details will be shown, where the option to update details of it (except id) will be.

DELETE -> a user can tap his finger on a note and more details will be shown, where there will be a delete button next to the update one.


### Persistence

Every CRUD operation will be persisted on the server as well. Local storage will only come into play if the device is offline (there will be a cache for that).

### Offline access

In the case that the device is offline, local storage will be implemented such that any crud operations will be saved locally in a cache, which will periodically try to upload everything in the hopes that the device is back online at some point. This will continue until we have received internet connection.

If I create a note it is saved locally in the cache, the same for update. For delete we will only store ids locally not the entire note, and then for read we will read the last cache of notes. So not the actual notes on the server but something that was recently fetched when we were online.

### Design

[Link will be here soon](link)
