Donwload image Docker from: https://drive.google.com/file/d/1DKt8_S-bSY-sB4P0__J5ZiPPG86KUEC_/view?usp=drive_link

Load Docker Image

docker load -i orders-service.tar

Run Docker container

docker run --name orders-rest -p 3000:3000 orders-service-dummy
