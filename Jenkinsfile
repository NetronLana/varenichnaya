pipeline {
    agent any

    tools {
        jdk 'jdk21'
        maven 'maven3'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
    stage('SonarQube Analysis') {
    steps {
        withSonarQubeEnv('SonarQube') {
            bat 'mvn sonar:sonar'
        }
    }
}

        stage('Build and Test') {
            steps {
                bat 'mvn clean verify'
            }
        }

    }

    post {
        always {
            junit 'target/surefire-reports/*.xml'
        }
    }
}
