pipeline {
    agent any

    environment { 
        AWS_REGION = "us-east-1" 
        STACK_NAME = "my-prod-stack" 
        TEMPLATE_FILE = "template.yml"
    }
    

    stages {
        stage('Install AWS CLI') {
            steps {
                sh '''
                curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o awscliv2.zip
                unzip awscliv2.zip
                ./aws/install
                aws --version
                '''
            }
        }
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
