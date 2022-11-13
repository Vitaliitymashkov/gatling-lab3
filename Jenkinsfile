pipeline {
    agent any

    stages {
        stage('Build') {
               steps {
                    container ('maven'){
                    sh 'mvn version'
                    }
                }
        stage("Build Maven 3") {
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