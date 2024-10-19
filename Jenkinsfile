node {
    // Environment variables
    def MAVEN_HOME = 'C:/Program Files/apache-maven-3.9.7' // Adjust to your Maven configuration in Jenkins
    def JAVA_HOME = 'C:/Program Files/Java/jdk-17.0.12' // Adjust to your JDK configuration in Jenkins
    def SONAR_HOST_URL = 'http://localhost:9000' // Adjust this to your SonarQube server URL (local or remote)
    def JFROG_URL = 'http://localhost:8082/artifactory/test-libs-release/' // Artifactory URL
    def JAR_PATH = 'C:/Users/Sachin/.jenkins/workspace/Sonar/target/Redbus-0.0.1-SNAPSHOT.jar' // Path to the JAR file

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
            // Run SonarQube analysis using Maven and fetch the SonarQube token from Jenkins credentials
            withCredentials([string(credentialsId: 'sonar_token', variable: 'SONAR_TOKEN')]) {
                echo 'Running SonarQube analysis...'
                bat "\"${MAVEN_HOME}/bin/mvn\" sonar:sonar -Dsonar.host.url=${SONAR_HOST_URL} -Dsonar.login=${sonar_token}"
            }
        }

        stage('Upload to JFrog Artifactory') {
            // Upload the built JAR file to JFrog Artifactory using token from Jenkins credentials
            withCredentials([string(credentialsId: 'jfrog_token', variable: 'JFROG_TOKEN')]) {
                echo 'Uploading artifact to JFrog Artifactory...'
                bat "curl -u admin:${jfrog_token} -T \"${JAR_PATH}\" \"${JFROG_URL}Redbus-0.0.1-SNAPSHOT.jar\""
            }
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
