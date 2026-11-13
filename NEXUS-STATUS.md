# Nexus Drive: experimental fork

Based on TeleDrive by Mahmud0808 and contributors; original Apache-2.0 license and history are retained. Modified upstream files carry notices.

Folder colors and emoji are local display preferences keyed by channel and folder ID. They do not change Telegram folder records, file manifests, encryption or folder names. They do not sync to other devices, are not visible in TeleDrive, and can be lost when app data is cleared. Folder name editing uses the upstream operation. The selected color is shown in Files list/grid; other views retain the original icon.

The debug APK uses a different application ID and debug signing so it can be installed separately; keys and local data are not shared with the original. Builds may use different debug signing keys. Do not uninstall the original or use irreplaceable data. Automatic upstream update checking is off by default in this fork.

No claim of full bidirectional interoperability: test both directions on a disposable private channel and accounts, with unencrypted and encrypted files, nested and empty folders, changes, and recovery passphrase, then verify bytes and metadata on both clients. Never send real credentials to a third party. No Telegram data was accessed by this build.
