pipeline {
    agent any
    tools{
        maven 'maven386'
    }
    stages{
        stage('Maven Build'){
            steps{
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/ranjanpandeysbp/order-service']]])
                bat 'mvn clean install'
            }
        }
        stage('Running Test') {
            steps {
                bat "mvn clean test"
            }
        }
    }
}
