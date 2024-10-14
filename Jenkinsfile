node {
    // Environment variables
    def MAVEN_HOME = 'C:/Program Files/apache-maven-3.9.7' // Adjust to your Maven configuration in Jenkins
    def JAVA_HOME = 'C:/Program Files/Java/jdk-17.0.12' // Adjust to your JDK configuration in Jenkins
    def SONAR_TOKEN = 'sqa_6be189e6d6ebefbf625f2cae986413372e9a4ccb' // Your SonarQube token
    def SONAR_HOST_URL = 'http://localhost:9000' // Adjust this to your SonarQube server URL (local or remote)
    
    try {
        stage('Clone Repository') {
            // Clone the repository from GitHub
            git 'https://github.com/Deepika11419/devops_session_2024.git'
        }

        stage('Build') {
            // Build the project using Maven (skip tests during the build stage)
            echo 'Building the project...'
            bat "\"${MAVEN_HOME}/bin/mvn\" clean install -DskipTests"
        }

        stage('Test') {
            // Run TestNG tests using Maven
            echo 'Running TestNG tests...'
            bat "\"${MAVEN_HOME}/bin/mvn\" clean test -PWholeSuite"
        }
        
        stage('Publish TestNG Results') {
            // Publish the TestNG results
            junit '**/surefire-reports/*.xml' // Ensure this matches your actual report path
        }
        
        stage('SonarQube Analysis') {
            // Run SonarQube analysis using Maven and pass the authentication token
            echo 'Running SonarQube analysis...'
            bat "\"${MAVEN_HOME}/bin/mvn\" sonar:sonar -Dsonar.host.url=${SONAR_HOST_URL} -Dsonar.login=${SONAR_TOKEN}"
        }

        stage('Upload to JFrog Artifactory') {
            // Upload the built JAR file to JFrog Artifactory
            echo 'Uploading artifact to JFrog Artifactory...'
            bat "curl -u admin:eyJ2ZXIiOiIyIiwidHlwIjoiSldUIiwiYWxnIjoiUlMyNTYiLCJraWQiOiJieGZGcnkyT3Q2dThiaGZRaHNVYUtORWg3Q19WX3BpNjA4LXFvOG9GN3RFIn0.eyJzdWIiOiJqZmFjQDAxamE1YWpnNzVlNHo0MDZnYmZtemsxM3NrL3VzZXJzL2FkbWluIiwic2NwIjoiYXBwbGllZC1wZXJtaXNzaW9ucy9hZG1pbiIsImF1ZCI6IipAKiIsImlzcyI6ImpmZmVAMDFqYTVhamc3NWU0ejQwNmdiZm16azEzc2siLCJpYXQiOjE3Mjg5MDQzMTMsImp0aSI6IjU0MTAyODNiLTVmNTItNDM2Ny05ZTkzLTQyY2QxNTI0NGY1OSJ9.GgRzkp9b50opZDUYU2qG89JyGccFsRIoibksJ1NutPX2WCDUd-W0vxkZIz2plUTpIAJP2lvNz7DirV9xakjSW-j9lZ-OpFKt-2RwsS-qlkpkIdNmKQGkL6FScOmYHx5d6OIyUKUG1Y3PW6yptlfuxTyjQVzEUT_8iyzHMcjUtkciXcKuE7ooxNsi_PQHwycmNVNK_PJO5NBgjCm1z9UPicf1YGbcGcKFGcx801dYfSZ9eimZ7sCHLpHOEilozZ-_mCx2jaGAaNSxOPARXAt0o5CcgrxGU8JbBrxVO7-ruJSY8fKxmXo-o8WkpLmpFurNmF3KM147DGXFaC5QMBGcsg -T \"${JAR_PATH}\" \"${JFROG_URL}Redbus-0.0.1-SNAPSHOT.jar\""
        }
        
    } catch (Exception e) {
        // Handle any exceptions
        echo "An error occurred: ${e.message}"
        currentBuild.result = 'FAILURE'
    } finally {
        // Clean-up steps if required
        echo 'Cleaning up...'
    }
}
