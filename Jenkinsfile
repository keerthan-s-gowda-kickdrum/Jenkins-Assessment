pipeline {
    agent any

    parameters {
        choice(name: 'BROWSER', choices: ['chrome', 'firefox', 'edge'], description: 'Choose the browser for test execution')
    }

    triggers {
        cron('30 22 * * 4') // Runs every Thursday at 10:30 PM
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    echo 'Checking out code from GitHub...'
                    git credentialsId: 'github-jenkins-creds',
                        branch: 'master',
                        url: 'https://github.com/keerthan-s-gowda-kickdrum/Jenkins-Assessment.git'
                    echo 'Checkout completed successfully.'
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    try {
                        echo 'Starting Maven build...'
                        bat "mvn clean compile"
                        echo 'Build completed successfully.'
                    } catch (Exception e) {
                        error "Build failed. Check logs for details."
                    }
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    echo "Executing tests on: ${params.BROWSER}"
                    try {
                        bat "mvn test -Dbrowser=${params.BROWSER}"
                        echo 'Test execution completed successfully.'
                    } catch (Exception e) {
                        error "Test execution failed. Check logs for details."
                    }
                }
            }
        }

stage('Generating the Report') {
            steps {
                script {
                    echo 'Generating test reports...'
                    bat "mvn surefire-report:report"
                    echo 'Report generation completed.'
                }
            }
        }

        stage('Publish HTML Report') {
            steps {
                echo 'Publishing test report...'
                publishHTML(target: [
                    reportDir: 'target/surefire-reports',
                    reportFiles: 'index.html',
                    reportName: 'Test Report',
                    keepAll: true
                ])
                echo 'Report published successfully.'
            }
        }
    }


    post {
        always {
            echo 'Pipeline execution completed.'
        }
    }
}
