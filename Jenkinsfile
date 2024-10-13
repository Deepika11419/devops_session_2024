node {
    // Environment variables
    def MAVEN_HOME = 'C:/Program Files/apache-maven-3.9.7' // Adjust to your Maven configuration in Jenkins
    def JAVA_HOME = 'C:/Program Files/Java/jdk-17.0.12' // Adjust to your JDK configuration in Jenkins
    
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
            bat '"C:/Program Files/apache-maven-3.9.7/bin/mvn" clean test -PWholeSuite'
        }
        
        stage('Publish TestNG Results') {
        junit '**/surefire-reports/*.xml' // Ensure this matches your actual report path
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
