# BlockListView
> In development

Android library for displaying and managing hierarchical lists of blocks and events with drag-and-drop support.

## Installation

Usage / see `demo/`

```java
BlockListView blockListView = findViewById(R.id.block_list_view);

// Create events and blocks
EventItem event = new EventItem("Event 1");
event.addBlock(new BlockItem("Block 1"));
event.addBlock(new BlockItem("Block 2"));

List<EventItem> events = Arrays.asList(event);
blockListView.setEvents(events);

// Add new event
EventItem newEvent = new EventItem("New Event");
blockListView.addEvent(newEvent);

// Add block to event
BlockItem newBlock = new BlockItem("New Block");
blockListView.addBlockToEvent(event, newBlock);

// Remove item
blockListView.remove(item);

// Toggle event visibility
blockListView.toggleEvent(event);
```

Data Structure

· BaseItem: Base class with name, layout, background
· EventItem: Container for blocks (expandable/collapsible)
· BlockItem: Individual block belonging to an event

Gestures

· Long press to start dragging
· Drag items up/down
· Swipe left to delete items

Customization

Override layouts and styles:

· R.layout.event_item - Event layout
· R.layout.block_item - Block layout
· R.menu.event_actions - Event context menu
· R.menu.block_actions - Block context menu

License

```
Copyright (C) 2023 Xedox

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
```
