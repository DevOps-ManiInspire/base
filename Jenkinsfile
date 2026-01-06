pipeline {
    agent any

    stages {
        stage('Deploy') {
            steps {
                script {
                    // Load the external Groovy file
                    def utils = load 'cft_utils.groovy'

                    // Call the function inside it
                    utils.update_cf("my-prod-stack")
                }
            }
        }
    }
}
