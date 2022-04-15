	def buildone() {
		echo 'Building the Application'
		echo "Building version ${NEW_VERSION}"
		sh "mvn install"
		when {
			expression {
				env.BRANCH_NAME == 'main' || env.BRANCH_NAME == 'dev' && CODE_CHANGES == true
	}
	
## Reference: https://github.com/GoogleCloudPlatform/continuous-deployment-on-kubernetes/blob/master/sample-app/Jenkinsfile
	def testone() {
		container('##Container Description') {
          sh """
            ln -s `pwd` /go/src/mynk-litecoin
            cd /go/src/mynk-litecoin
            go test
          """
        }
      }
	
## Reference: https://www.jenkins.io/doc/pipeline/steps/anchore-container-scanner/
## Reference: https://anchore.com/blog/inline-scanning-with-anchore-engine/
	def scanone () {
		sh 'docker build -t ${IMAGE_NAME}:ci .'
		sh 'apk add bash curl'
        sh 'curl -s https://ci-tools.anchore.io/inline_scan-v0.6.0 | bash -s -- -d Dockerfile -b .anchore_policy.json ${IMAGE_NAME}:ci'
		withDockerRegistry([credentialsId: "dockerhub-creds", url: ""]){
			sh 'docker tag ${IMAGE_NAME}:ci ${IMAGE_NAME}:${IMAGE_TAG}'
			sh 'docker push ${IMAGE_NAME}:${IMAGE_TAG}'
	}
	
	def deployone() {
		echo 'Deploying the Application'
		echo "Deploying using ${SERVER_CREDENTIALS}"
		sh "${SERVER_CREDENTIALS}"
		container('kubectl') {
		kubectl create -f mynk-litecoin.yaml
		}
	}
	
	return this	