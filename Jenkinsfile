pipeline {
    agent any
    tools {
        maven 'Maven 3.8.6'
        jdk 'Java 17.0.4.1'
    }
    stages {
        stage("Build Maven") {
            steps {
                sh 'mvn -B clean package'
            }
        }
        stage("Run Gatling") {
            steps {
                sh 'mvn gatling:test'
            }
            post {
                always {
                    gatlingArchive()
                }
            }
        }
    }
}