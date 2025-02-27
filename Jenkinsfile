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
                        echo 'Build failed! Skipping tests.'
                        error "Build failed. Skipping tests."
                    }
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    def selectedBrowser = params.BROWSER
                    echo "Selected browser: ${selectedBrowser}"
                    echo 'Starting test execution...'
                    bat "mvn test -Dbrowser=${selectedBrowser}"
                    echo 'Test execution completed.'
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
            echo 'Archiving artifacts...'
            archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
            echo 'Artifacts archived successfully.'
        }
    }
}
