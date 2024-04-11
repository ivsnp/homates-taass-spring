# HoMates: simplifying the cohabitation

University of Turin, Computer Science Department

TAASS: Tecniche e architetture avanzate per lo sviluppo del software

year 2023-24

Prof. Giovanna Petrone

Students: 
- Ivan Spada
- Mattia Ladisa
- Giorgia Iacobellis

---


# How to

This section contains a tutorial to update the images on Docker Hub (if needed) and set up minikube.



## Docker Hub
Run on terminal the following commands (or just a sample that corresponds to the microservices changed):

```bash
docker build ./api-gateway --tag ivsnp/homates-apigateway:1.0.0 && docker push ivsnp/homates-apigateway:1.0.0 

docker build ./bacheca --tag ivsnp/homates-board:1.0.0 && docker push ivsnp/homates-board:1.0.0 

docker build ./calendar --tag ivsnp/homates-calendar:1.0.0 && docker push ivsnp/homates-calendar:1.0.0 

docker build ./eureka-service --tag ivsnp/homates-eureka:1.0.0 && docker push ivsnp/homates-eureka:1.0.0 

docker build ./react --tag ivsnp/homates-react:1.0.0 && docker push ivsnp/homates-react:1.0.0 

docker build ./shopping-list --tag ivsnp/homates-shopl:1.0.0 && docker push ivsnp/homates-shopl:1.0.0 

docker build ./user-houses --tag ivsnp/homates-userhouses:1.0.0 && docker push ivsnp/homates-userhouses:1.0.0 

docker build ./wallet --tag ivsnp/homates-wallet:1.0.0 && docker push ivsnp/homates-wallet:1.0.0 
```

*Note: 'ivsnp' should be changed with your Docker Hub username, remember to updated the docker-compose and the minikube configuration.*



## Minikube [Linux]

First of all, install Minikube: https://minikube.sigs.k8s.io/docs/start/ 



**[First time]**

Run on terminal the following commands:

```bash
# create and start minikube using virtualbox with 4 cpus and 6GB of memory
minikube start --driver=virtualbox --cpus=4 --memory=6144

# enable metrics-server addon
minikube addons enable metrics-server

# enable ingress controller
minikube addons enable ingress

# get minikube ip
minikube ip
# example output: 
# 192.168.59.102

# add minikube ip to /etc/hosts file linking it to the app node ingresses
sudo nano /etc/hosts
# example edit:
# End of section
# 192.168.59.102  api.homates.it
# 192.168.59.102  webui.homates.it
# 192.168.59.102  eureka.homates.it
# 192.168.59.102  pgadmin.homates.it

# check edits on /etc/hosts file
cat /etc/hosts

# move to a specific namespace
kubectl config set-context --current --namespace=homates

# apply minikube manifests
kubectl apply -f ./kubeconf/homates-namespace.yaml
kubectl apply -f ./kubeconf/00_init/
kubectl apply -f ./kubeconf/01_dbs/
kubectl apply -f ./kubeconf/02_microservices/

# check ingress
kubectl get ingress --namespace=homates

# check pods status, more infos and wait untill all of them have 'Running' status, otherwise debug
watch kubectl get all -n homates

# check services
kubectl get services

# check persistent volumes
kubectl get pv

# check persistent volume claims
kubectl get pvc

# see usage metrics, needs metrics-server addon (two alternatives)
watch kubectl top pods

# stop minikube 
minikube stop
```

If it were necessary to have the public kubernetes cluster on cloud, it would be useful to use load balancer to improve traffic management:

```bash
# loadbalancer
minikube addons enable metallb
minikube addons configure metallb  # requires nginx deployment with multiple replicas
```



**[Run when Minikube is already set up]**

Run on terminal the following commands:

```bash
# start minikube
minikube start

# move to a specific namespace
kubectl config set-context --current --namespace=homates

# check ingress
kubectl get ingress --namespace=homates

# check pods status and wait untill all of them have 'Running' status, otherwise debug
watch kubectl get all -n homates

# check services
kubectl get services

# check persistent volume
kubectl get pv

# check persistent volume claim
kubectl get pvc

# see usage metrics, needs metrics-server addon (two alternatives)
watch kubectl top pods

# magic is in your hands

# stop minikube 
minikube stop
```



**[Debugging and other commands]**

Run on terminal the following commands:

```bash
# check minikube status
minikube status

# check ingress
kubectl get ingress --namespace=homates

# check pods status (two alternatives)
kubectl -n homates get pods -w
watch kubectl get all -n homates

# check pod logs (two alternatives)
kubectl describe pods pod-name-XXX
kubectl logs -f pod-name-XXX

# check services
kubectl get services

# check persistent volume
kubectl get pv

# check persistent volume claim
kubectl get pvc

# see usage metrics, needs metrics-server addon (two alternatives)
kubectl top pods
watch kubectl top pods

# apply configuration changes
kubectl apply -f ./kubeconf/...
```

