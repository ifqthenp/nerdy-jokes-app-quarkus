USERNAME			:= ifqthenp
COMMIT				:= $(shell git rev-parse HEAD)

DESCRIBE			:= $(shell git describe --match "v*" --always --tags)
DESCRIBE_PARTS		:= $(subst -, ,$(DESCRIBE))
VERSION_TAG			:= $(word 1,$(DESCRIBE_PARTS))

# >>> NERDY JOKES APP JVM >>> ##########################################################################################

.PHONY: app_jvm
app_jvm: build_app_jvm build_app_docker_jvm push_app_docker_jvm

APP_DOCKERFILE_JVM	:= src/main/docker/Dockerfile.jvm
APP_IMAGE_NAME_JVM	:= ${USERNAME}/nerdy-jokes-app-quarkus-jvm
APP_IMG_JVM			:= ${APP_IMAGE_NAME_JVM}:${COMMIT}
APP_TAG_JVM			:= ${APP_IMAGE_NAME_JVM}:${VERSION_TAG}
APP_LATEST_JVM		:= ${APP_IMAGE_NAME_JVM}:latest

.PHONY: build_app_jvm
build_app_jvm:
	@./mvnw clean package

.PHONY: build_app_docker_jvm
build_app_docker_jvm:
	@DOCKER_BUILDKIT=1 docker image build -f ${APP_DOCKERFILE_JVM} -t ${APP_IMG_JVM} .
	@docker tag ${APP_IMG_JVM} ${APP_LATEST_JVM}
	@docker tag ${APP_IMG_JVM} ${APP_TAG_JVM}

.PHONY: push_app_docker_jvm
push_app_docker_jvm:
	@docker push ${APP_IMAGE_NAME_JVM}

.PHONY: run_app_jvm
run_app_jvm:
	java -jar ./target/nerdy-jokes-app-quarkus-*-runner.jar

.PHONY: run_app_docker_jvm
run_app_docker_jvm:
	@docker run -it --rm -p 8080:8080 ${APP_LATEST_JVM}

# >>> NERDY JOKES APP NATIVE >>> #######################################################################################

.PHONY: app_native
app_native:
	@./mvnw clean package -Pnative

.PHONY: app_docker_native
app_docker_native: build_app_docker_native push_app_docker_native

APP_DOCKERFILE_NATIVE	:= src/main/docker/Dockerfile.native
APP_IMAGE_NAME_NATIVE	:= ${USERNAME}/nerdy-jokes-app-quarkus-native
APP_IMG_NATIVE			:= ${APP_IMAGE_NAME_NATIVE}:${COMMIT}
APP_TAG_NATIVE			:= ${APP_IMAGE_NAME_NATIVE}:${VERSION_TAG}
APP_LATEST_NATIVE		:= ${APP_IMAGE_NAME_NATIVE}:latest

.PHONY: build_app_docker_native
build_app_docker_native:
	@./mvnw clean package -Pnative -Dquarkus.native.container-build=true
	@DOCKER_BUILDKIT=1 docker build -f ${APP_DOCKERFILE_NATIVE} -t ${APP_IMG_NATIVE} .
	@docker tag ${APP_IMG_NATIVE} ${APP_LATEST_NATIVE}
	@docker tag ${APP_IMG_NATIVE} ${APP_TAG_NATIVE}

.PHONY: push_app_docker_native
push_app_docker_native:
	@docker push ${APP_IMAGE_NAME_NATIVE}

.PHONY: run_app_native
run_app_native:
	@./target/nerdy-jokes-app-quarkus-*-runner

.PHONY: run_app_docker_native
run_app_docker_native:
	@docker run -it --rm -p 8080:8080 ${APP_LATEST_NATIVE}

## >>> DOCKER >>> ######################################################################################################

docker_clean:
	-docker container rm $$(docker container ls -qa)
	-docker image prune -a
