install:
	./gradlew dependencies

test:
	./gradlew test

test-coverage:
	./gradlew jacocoTestReport

lint:
	@echo "Running lint checks on application"
	./gradlew detektAll

build:
	@echo "Building application"
	./gradlew build -x test

all: install lint test