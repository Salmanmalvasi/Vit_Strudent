# 🎓 StudentCC - VIT Student Dashboard

[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)
[![Platform](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com/)
[![API](https://img.shields.io/badge/API-21+-orange.svg)](https://developer.android.com/about/versions/android-5.0)
[![Version](https://img.shields.io/badge/Version-1.0.0-brightgreen.svg)](https://github.com/Salmanmalvasi/Vit_Strudent/releases)

> **StudentCC** is an unofficial Android application designed to provide VIT students with easy access to their academic information through a modern, user-friendly interface.

## 📱 Screenshots

<div align="center">
  <img src="https://via.placeholder.com/300x600/2196F3/FFFFFF?text=Login+Screen" alt="Login Screen" width="150"/>
  <img src="https://via.placeholder.com/300x600/4CAF50/FFFFFF?text=Home+Screen" alt="Home Screen" width="150"/>
  <img src="https://via.placeholder.com/300x600/FF9800/FFFFFF?text=Performance" alt="Performance" width="150"/>
  <img src="https://via.placeholder.com/300x600/9C27B0/FFFFFF?text=Profile" alt="Profile" width="150"/>
</div>

## ✨ Features

### 🏠 **Home Dashboard**
- Quick access to all academic features
- Real-time notifications and updates
- Easy navigation to different sections

### 📊 **Performance Tracking**
- **Marks & Grades**: View detailed marks for all subjects
- **GPA Calculator**: Calculate semester and cumulative GPA
- **Attendance**: Track attendance percentage for each course
- **Progress Charts**: Visual representation of academic progress

### 📚 **Course Management**
- **Course List**: View all enrolled courses
- **Course Details**: Detailed information about each course
- **Faculty Information**: Contact details and information about faculty
- **Venue & Schedule**: Class timings and locations

### 📅 **Timetable**
- **Daily Schedule**: View classes for each day
- **Weekly Overview**: Complete weekly timetable
- **Real-time Updates**: Live schedule changes
- **Navigation**: Easy day-wise navigation

### 📝 **Exams & Assignments**
- **Exam Schedule**: Upcoming exam dates and times
- **Assignment Deadlines**: Track assignment submissions
- **Exam Notifications**: Get notified about upcoming exams
- **Seat Information**: Exam venue and seat details

### 🎨 **Personalization**
- **Multiple Themes**: Choose from 5 different color themes
  - 🔴 Red Theme
  - 🔵 Blue Theme
  - 🟣 Purple Theme
  - 🟢 Green Theme
  - ⚫ Black Theme
- **Dark Mode Support**: Automatic dark/light mode switching
- **Customizable Interface**: Personalize your experience

### 🔔 **Smart Notifications**
- **Exam Reminders**: Never miss an exam
- **Assignment Deadlines**: Stay on top of submissions
- **Attendance Alerts**: Track your attendance
- **Important Updates**: Get notified about announcements

### 📋 **Profile & Settings**
- **Student Information**: View and manage profile
- **Privacy Policy**: Built-in privacy policy viewer
- **Open Source**: Link to GitHub repository
- **Theme Selection**: Easy theme switching
- **App Settings**: Customize app behavior

## 🛠️ Technical Features

- **Modern UI/UX**: Material Design 3 implementation
- **Offline Support**: Cache data for offline access
- **Secure Authentication**: Safe login with VIT credentials
- **Data Privacy**: No personal data stored externally
- **Performance Optimized**: Fast and responsive interface
- **Accessibility**: Support for accessibility features

## 📋 Requirements

- **Android Version**: API 21+ (Android 5.0 Lollipop)
- **Internet Connection**: Required for data synchronization
- **VIT Student Account**: Valid VIT student credentials
- **Storage**: Minimum 50MB free space

## 🚀 Installation

### Method 1: Download APK
1. Download the latest APK from [Releases](https://github.com/Salmanmalvasi/Vit_Strudent/releases)
2. Enable "Install from Unknown Sources" in your Android settings
3. Install the APK file
4. Open the app and login with your VIT credentials

### Method 2: Build from Source
1. Clone the repository:
   ```bash
   git clone https://github.com/Salmanmalvasi/Vit_Strudent.git
   cd Vit_Strudent
   ```

2. Open the project in Android Studio

3. Sync Gradle files and build the project

4. Run on your device or emulator

## 🔧 Development Setup

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK API 21+
- Java 8 or later
- Gradle 7.0+

### Build Instructions
```bash
# Clone the repository
git clone https://github.com/Salmanmalvasi/Vit_Strudent.git

# Navigate to project directory
cd Vit_Strudent

# Open in Android Studio
# Or build from command line
./gradlew assembleDebug
```

## 📱 Usage

### First Time Setup
1. **Launch App**: Open StudentCC from your app drawer
2. **Login**: Enter your VIT student credentials
3. **Grant Permissions**: Allow necessary permissions for notifications
4. **Sync Data**: Wait for initial data synchronization
5. **Explore**: Navigate through different sections

### Navigation
- **Bottom Navigation**: Switch between Home, Performance, and Profile
- **Quick Actions**: Use home screen quick action buttons
- **Menu**: Access settings and additional features from profile

### Theme Customization
1. Go to **Profile** → **Application** → **Appearance**
2. Select your preferred theme color
3. Choose between light and dark mode
4. Changes apply immediately

## 🏗️ Architecture

```
app/
├── activities/          # Android Activities
├── fragments/          # UI Fragments
├── adapters/           # RecyclerView Adapters
├── models/             # Data Models
├── helpers/            # Helper Classes
├── services/           # Background Services
├── interfaces/         # Database Interfaces
└── res/               # Resources (layouts, drawables, etc.)
```

## 🛡️ Privacy & Security

- **No External Storage**: All data is stored locally on your device
- **Secure Authentication**: Uses VIT's official authentication system
- **Privacy Policy**: Built-in privacy policy with detailed information
- **Open Source**: Transparent codebase for community review
- **No Tracking**: No analytics or tracking mechanisms

## 📄 License

This project is licensed under the **GNU General Public License v3.0** - see the [LICENSE](LICENSE) file for details.

### License Summary
- ✅ **Free to use**: You can use this software for any purpose
- ✅ **Free to modify**: You can modify the source code
- ✅ **Free to distribute**: You can share the software
- ⚠️ **Copyleft**: Any derivative works must also be GPLv3

## ⚠️ Disclaimer

**This is an unofficial application and is not affiliated with VIT University.**

- This app is developed independently by students for students
- Use at your own discretion
- The developers are not responsible for any data loss or issues
- Always verify information with official VIT sources

## 🤝 Contributing

We welcome contributions! Here's how you can help:

### How to Contribute
1. **Fork** the repository
2. **Create** a feature branch (`git checkout -b feature/AmazingFeature`)
3. **Commit** your changes (`git commit -m 'Add some AmazingFeature'`)
4. **Push** to the branch (`git push origin feature/AmazingFeature`)
5. **Open** a Pull Request

### Contribution Guidelines
- Follow Android development best practices
- Maintain code style consistency
- Add proper documentation
- Test your changes thoroughly
- Update README if needed

### Areas for Contribution
- 🐛 **Bug Fixes**: Report and fix bugs
- ✨ **New Features**: Add useful features
- 📱 **UI/UX**: Improve user interface
- 📚 **Documentation**: Enhance documentation
- 🔧 **Performance**: Optimize app performance

## 📞 Support

### Getting Help
- **Issues**: Report bugs and request features on [GitHub Issues](https://github.com/Salmanmalvasi/Vit_Strudent/issues)
- **Discussions**: Join discussions on [GitHub Discussions](https://github.com/Salmanmalvasi/Vit_Strudent/discussions)
- **Documentation**: Check the code comments and this README

### Common Issues
- **Login Problems**: Ensure you're using correct VIT credentials
- **Sync Issues**: Check your internet connection
- **Theme Not Working**: Restart the app after theme change
- **Data Not Loading**: Try logging out and logging back in

## 🙏 Acknowledgments

- **VIT University**: For providing the VTOP platform
- **Android Community**: For open source libraries and tools
- **Material Design**: For the design system
- **Contributors**: Everyone who has contributed to this project

## 📊 Project Statistics

![GitHub stars](https://img.shields.io/github/stars/Salmanmalvasi/Vit_Strudent)
![GitHub forks](https://img.shields.io/github/forks/Salmanmalvasi/Vit_Strudent)
![GitHub issues](https://img.shields.io/github/issues/Salmanmalvasi/Vit_Strudent)
![GitHub pull requests](https://img.shields.io/github/issues-pr/Salmanmalvasi/Vit_Strudent)

## 🌟 Star History

[![Star History Chart](https://api.star-history.com/svg?repos=Salmanmalvasi/Vit_Strudent&type=Date)](https://star-history.com/#Salmanmalvasi/Vit_Strudent&Date)

---

<div align="center">
  <p><strong>Made with ❤️ by VIT Students for VIT Students</strong></p>
  <p>If this project helps you, please give it a ⭐</p>
</div>
