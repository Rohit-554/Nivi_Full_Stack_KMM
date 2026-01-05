This is a Kotlin Multiplatform project targeting Android, iOS, Server.

* [/composeApp](./composeApp/src) is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - [commonMain](./composeApp/src/commonMain/kotlin) is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    the [iosMain](./composeApp/src/iosMain/kotlin) folder would be the right place for such calls.
    Similarly, if you want to edit the Desktop (JVM) specific part, the [jvmMain](./composeApp/src/jvmMain/kotlin)
    folder is the appropriate location.

* [/iosApp](./iosApp/iosApp) contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform,
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

* [/server](./server/src/main/kotlin) is for the Ktor server application.

* [/shared](./shared/src) is for the code that will be shared between all targets in the project.
  The most important subfolder is [commonMain](./shared/src/commonMain/kotlin). If preferred, you
  can add code to the platform-specific folders here too.

### Build and Run Android Application

To build and run the development version of the Android app, use the run configuration from the run widget
in your IDE’s toolbar or build it directly from the terminal:
- on macOS/Linux
  ```shell
  ./gradlew :composeApp:assembleDebug
  ```
- on Windows
  ```shell
  .\gradlew.bat :composeApp:assembleDebug
  ```

### Build and Run Server

#### Prerequisites
- JDK 17 or higher installed
- Docker Desktop (for running PostgreSQL database)

#### Step 1: Install Docker Desktop
1. Download Docker Desktop for your operating system:
   - macOS: https://www.docker.com/products/docker-desktop/
   - Windows: https://www.docker.com/products/docker-desktop/
   - Linux: https://docs.docker.com/desktop/install/linux-install/

2. Install Docker Desktop following the installer instructions

3. Start Docker Desktop application

4. Verify Docker is running:
   ```shell
   docker --version
   ```
   You should see output like: `Docker version 24.x.x`

#### Step 2: Setup PostgreSQL Database
1. Navigate to the server directory from project root:
   ```shell
   cd server
   ```

2. Start PostgreSQL and pgAdmin containers using Docker Compose:
   ```shell
   docker-compose up -d
   ```
   The `-d` flag runs containers in detached mode (background)

3. Verify containers are running successfully:
   ```shell
   docker ps
   ```
   You should see two containers:
   - `nivi_db` (PostgreSQL database)
   - `nivi_admin` (pgAdmin web interface)

4. Wait 10-15 seconds for PostgreSQL to fully initialize

5. Database connection details (configured in docker-compose.yml):
   - Host: `localhost`
   - Port: `5433`
   - Database Name: `nivi_db`
   - Username: `myuser`
   - Password: `mypassword`

6. Optional - Access pgAdmin web dashboard:
   - Open browser and navigate to: http://localhost:5050
   - Login credentials:
     - Email: `admin@admin.com`
     - Password: `admin`
   - To connect to database in pgAdmin:
     - Right-click Servers > Register > Server
     - General tab: Name = `Nivi DB`
     - Connection tab:
       - Host: `postgres_db`
       - Port: `5432`
       - Database: `nivi_db`
       - Username: `myuser`
       - Password: `mypassword`

#### Step 3: Run the Ktor Server
Navigate back to the project root directory if you're still in the server folder:
```shell
cd ..
```

Then run the server:
- on macOS/Linux
  ```shell
  ./gradlew :server:run
  ```
- on Windows
  ```shell
  .\gradlew.bat :server:run
  ```

The server will start on http://localhost:8080

Expected output on successful startup:
```
Database connected successfully to Docker Postgres!
Application started: http://localhost:8080
```

#### Step 4: Stop the Database
To stop the PostgreSQL containers:
```shell
cd server
docker-compose down
```

To stop and remove all data:
```shell
docker-compose down -v
```

### Build and Run iOS Application

To build and run the development version of the iOS app, use the run configuration from the run widget
in your IDE’s toolbar or open the [/iosApp](./iosApp) directory in Xcode and run it from there.

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…