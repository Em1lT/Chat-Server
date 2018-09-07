

object Users{

    //var set = HashSet<String>()
    var nameList = mutableListOf<String>()
    var user = false


    fun insertUser(name: String): Boolean{

        if (!nameList.contains(name)){
            nameList.add(name)
            return true
        }else{
           return false
        }

    }

    fun checkUser(name: String):Boolean{
        return nameList.contains(name)

    }
    fun deleteUser(name: String){

            nameList.remove(name)
    }

}