./gradlew clean
./gradlew build
docker rmi -f $(docker images | grep 'userservice')
docker build -t userservice .

docker tag userservice gcr.io/gocarshare/userservice
docker push gcr.io/gocarshare/userservice

gcloud beta run deploy userservice --image gcr.io/gocarshare/userservice --platform managed --allow-unauthenticated  --region=us-east1
