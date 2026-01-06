pipeline {
    agent any

    environment { 
        AWS_REGION = "us-east-1" 
        STACK_NAME = "my-prod-stack" 
        TEMPLATE_FILE = "template.yml"
        PATH = "${env.HOME}/bin:${env.PATH}"
    }
    

    stages {
        stage('checkout') {
            steps {
               checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/DevOps-ManiInspire/base.git']])
            }
        }
        stage('Lint & Validate') { 
            steps { 
                sh '''aws cloudformation validate-template --template-body file://${TEMPLATE_FILE} ''' 
            } 
        }
    }
}
