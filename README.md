# Project NIVI
A financial intelligence application `In Progress` , fully developed using CMP & KTOR, designed to bridge the gap between daily expenses and future wealth. Unlike simple trackers, Nivi uses a "Desi" storytelling approach to teach users about investable surplus and compounding.

### Demo 
<img width="200" height="450" alt="image" src="https://github.com/user-attachments/assets/54e1af4b-6f95-448b-897a-cb42ce694850" />
<img width="200" height="450" alt="image" src="https://github.com/user-attachments/assets/1cc8fc78-b857-45e9-94eb-9ab34054f00f" />
<img width="200" height="450" alt="image" src="https://github.com/user-attachments/assets/dab51c7b-4cca-4f46-91a4-cd4b0a7274fc" />
<img width="200" height="450" alt="Screenshot_20260220_231108" src="https://github.com/user-attachments/assets/1ec1f529-d69e-4e1d-81b9-249c31b7a8ba" />
<img width="200" height="450" alt="Screenshot_20260220_231052" src="https://github.com/user-attachments/assets/6a5e30e2-e057-4a5a-948a-5857416855b8" />


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
