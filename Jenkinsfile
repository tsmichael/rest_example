pipeline {
    agent none
    stages {
        stage("Docker SetUp") {
            agent {label 'qweqwe'}
            steps { 
                //pull docker image
                sh 'docker pull swaggerapi/petstore3:latest'
                //run container on 9000 port
                sh 'docker run --name swaggerapi-petstore3 -d -p 9000:8080 swaggerapi/petstore3:latest'
                sh 'docker ps'
            }
        }
        stage("RUN API TESTS") {
            agent {label 'test_test'}
            tools {maven 'maven 3.6.3'}
            steps { 
                git branch: 'realrest', url: 'https://github.com/tsmichael/rest_example.git'
                sh 'mvn -f ${WORKSPACE}/Rest_Demo clean test' }
        }
        stage("Docker ShutDown") {
            agent {label 'qweqwe'}
            steps { 
                sh 'docker stop swaggerapi-petstore3'
                //remove container
                sh 'docker rm swaggerapi-petstore3'
                //remove image
                sh 'docker rmi swaggerapi/petstore3:latest'
            }
        }
        stage("Allure Report"){
            agent {label 'test_test'}
            steps {
            allure includeProperties: false, jdk: '', results: [[path: 'Rest_Demo/target/allure-results']]
            }
        }
    }
}
