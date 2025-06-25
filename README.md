# WearAlarmSync

A companion app for Android and Wear OS that restores the ability to snooze or dismiss phone alarms directly from your Wear OS watch.

## Features

- Detects alarm notifications on your phone
- Instantly syncs alarm state to your Wear OS watch
- Allows snooze/dismiss actions from the watch
- Closes the alarm screen on the watch if the alarm is handled on the phone
- Modern, Material-inspired UI

## How it works

1. **Phone app** listens for alarm notifications and sends sync messages to the watch.
2. **Wear app** displays an alarm screen with snooze/dismiss controls.
3. Actions on either device are reflected on the other for a seamless experience.

## Installation

### Prerequisites

- Android phone (API 31+)
- Wear OS watch (API 31+)

### Steps

1. Download and install the APKs for both phone and watch from the [Releases](https://github.com/nishant713/WearAlarmSync/releases) page.
2. Grant notification access to the phone app.
3. Pair your Wear OS watch with your phone.
4. Launch the app on both devices.

## Permissions

- **Notification Access:** Required to detect alarm notifications.
- **Wake Lock:** Keeps the screen on during alarms.

## Security & Privacy

- No data is sent to any server.
- All communication is local between your phone and watch.

## Contributing

Pull requests are welcome! For major changes, please open an issue first to discuss what you would like to change.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.