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
        
    } catch (Exception e) {
        // Handle any exceptions
        echo "An error occurred: ${e.message}"
        currentBuild.result = 'FAILURE'
    } finally {
        // Clean-up steps if required
        echo 'Cleaning up...'
    }
}
