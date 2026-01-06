pipeline {
    agent any

    environment { 
        AWS_REGION = "us-east-1" 
        STACK_NAME = "my-prod-stack" 
        TEMPLATE_FILE = "template.yml"
        PATH = "${env.HOME}/bin:${env.PATH}"
    }
    

    stages {
        stage('Install AWS CLI (runtime)') {
                        steps {
                            sh '''
                            set -e
            
                            rm -rf aws awscliv2.zip
            
                            curl "https://awscli.amazonaws.com/awscli-exe-linux-aarch64.zip" -o awscliv2.zip
                            unzip -q awscliv2.zip
            
                            ./aws/install \
                              -i $HOME/aws-cli \
                              -b $HOME/bin
            
                            export PATH=$HOME/bin:$PATH
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
