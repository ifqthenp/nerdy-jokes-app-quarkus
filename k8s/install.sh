#!/usr/bin/env bash

kind create cluster --config=kind-config.yaml

kubectl apply -f ./ingress-nginx/controller.yaml
kubectl apply -f ./ingress-nginx/metrics-svc.yaml
kubectl wait --namespace ingress-nginx \
    --for=condition=ready pod \
    --selector=app.kubernetes.io/component=controller \
    --timeout=90s

kubectl apply -f ./monitoring/prometheus/operator-bundle.yaml
kubectl apply -f ./monitoring/prometheus/instance.yaml
kubectl apply -f ./monitoring/prometheus/servicemonitor.yaml

kubectl apply -f ./ingress-nginx/servicemonitor.yaml

kubectl apply -f ./nerdy-jokes-app/deployment.yaml
kubectl apply -f ./nerdy-jokes-app/ingress.yaml
kubectl apply -f ./nerdy-jokes-app/servicemonitor.yaml

kubectl apply -f ./monitoring/grafana/prometheus-datasource-cm.yaml
kubectl apply -f ./monitoring/grafana/deployment.yaml
kubectl apply -f ./monitoring/grafana/servicemonitor.yaml

kubectl apply -f ./monitoring/ingress.yaml
