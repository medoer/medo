FROM node:alpine as builder
WORKDIR '/build'
COPY ./ ./payment-cashier

WORKDIR '/build/payment-cashier'
RUN npm install
# RUN npm rebuild node-sass
RUN npm run build

RUN ls /build/payment-cashier/dist

FROM nginx
RUN apt-get update
RUN apt-get -y install vim
EXPOSE 80
COPY --from=builder /build/payment-cashier/dist /usr/share/nginx/html


