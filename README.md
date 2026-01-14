# DMPlugin (Snipr)

Private messaging plugin for Hytale servers.

## Check us out
[Snipr](https://hytale.snipr.me/)

## Features
- **Direct Messages**: Send private messages to other players
- **Quick Reply**: Reply to the last person who messaged you
- **Multiple Aliases**: Use your preferred command style
- **Color-Coded**: Clear visual distinction between sent and received messages
- **Self-Message Prevention**: Can't accidentally message yourself

## Commands

### `/msg <player> <message>`
Send a private message to a player.

**Aliases**: `/dm`, `/tell`, `/whisper`, `/w`

**Example**: `/msg Notch Hello there!`

### `/reply <message>`
Reply to the last person who messaged you.

**Aliases**: `/r`

**Example**: `/r Thanks for the message!`

## Message Format
- **Received**: `[DM] SenderName → You: message`
- **Sent**: `[DM] You → RecipientName: message`

## Installation
1. Place `DMPlugin-1.0.0.jar` in your server's `plugins` folder
2. Restart the server
3. Plugin will automatically load

## Building
```bash
gradlew clean shadowJar
```

Output: `build/libs/DMPlugin-1.0.0.jar`

