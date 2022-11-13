pipeline {
    agent any
    tools {
        maven 'maven 3.8.6'
        jdk 'java 17.0.4.1'
    }
    stages {
        stage("Build Maven 2") {
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