FROM node:alpine as builder
WORKDIR '/build'
COPY ./ ./payment-cashier

WORKDIR '/build/payment-cashier'
RUN npm install
# RUN npm rebuild node-sass
RUN npm run build

RUN ls /build/payment-cashier/dist

FROM nginx
EXPOSE 80
COPY --from=builder /build/payment-cashier/dist /usr/share/nginx/html


