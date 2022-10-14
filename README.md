Библиотека, разработанная на курсах y_lab  
Для проверки работоспособности запустите Docker и введите в консоль:  
docker run --name library -p 5432:5432 -e POSTGRES_USER=test -e POSTGRES_PASSWORD=test -e POSTGRES_DB=mpl_ulab_db -d postgres
