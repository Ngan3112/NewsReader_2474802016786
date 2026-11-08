# Lab 15 manual verification checklist

1. Open the project in Android Studio and Gradle Sync; Assemble Project.
2. Launch on Pixel 6 emulator with network access. Expect ~100 posts. Verify Gson mode and manual mode each show 100.
3. Enable Airplane Mode on emulator. Tap Reload; confirm a readable error message and no crash.
4. Restore connectivity and Reload.
5. **Separate temporary debug experiments only (revert afterwards):**
   - Main-thread exception: in a temporary branch/debug build, invoke `URL(ApiConfig.POSTS_URL).openConnection().getInputStream()` directly on the main thread (e.g. in a button click listener); capture real Logcat exception; immediately revert.
   - Missing permission: remove `<uses-permission android:name="android.permission.INTERNET" />`, rebuild/reinstall and reload. Capture actual platform output (may be SecurityException or lower-level socket permission failure depending on device); restore permission afterward.
6. For each experiment capture actual stack trace, describe root cause, and add to a 1-page PDF report. Do not fabricate logs.
7. Push project to public GitHub and record full latest commit hash, then create final ZIP without `.gradle`, `.idea`, `build`, or `local.properties`.
