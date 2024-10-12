node {
    // Environment variables
    def MAVEN_HOME = 'C:\Program Files\apache-maven-3.9.7' // Adjust to your Maven configuration in Jenkins
    def JAVA_HOME = 'C:\Program Files\Java\jdk-17' // Adjust to your JDK configuration in Jenkins
    //def SONARQUBE_SERVER = 'http://your-sonarqube-server' // Define your SonarQube server URL
    //def SONARQUBE_TOKEN = credentials('your-sonarqube-token') // Jenkins credentials for SonarQube token

    try {
        stage('Clone Repository') {
            // Clone the repository from GitHub
            git 'https://github.com/Deepika11419/devops_session_2024.git'
        }

        stage('Build') {
            // Build the project using Maven
            echo 'Building the project...'
            bat "${MAVEN_HOME}\\bin\\mvn clean install -DskipTests"
        }

        stage('Test') {
            // Run tests using Maven and TestNG
            echo 'Running tests...'
            bat "${MAVEN_HOME}\\bin\\mvn test"

    
        }
      stage('Publish TestNG Results') {
            // Publish TestNG test results to Jenkins
            echo 'Publishing TestNG test results...'
            publishTestNGResults testResultsPattern: '**/target/testng-results.xml'
        }

    } catch (Exception e) {
        // Handle any exceptions
        echo "An error occurred: ${e.message}"
        currentBuild.result = 'FAILURE'
    } finally {
        // Clean up if needed
        echo 'Cleaning up...'
    }
}
       
