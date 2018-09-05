

object Users{

    //var set = HashSet<String>()
    var nameList = mutableListOf<String>()
    var user = false


    fun insertUser(name: String): String{

        if (!nameList.contains(name)){
            nameList.add(name)
            user = true
            return "Username$name created"
        }else{
            return "User already created"
        }

    }

    fun checkUser(name: String):Boolean{
        return nameList.contains(name)

    }
    fun deleteUser(name: String)
    {
        if (nameList.contains(name)){
            nameList.remove(name)
    }else{
            println("no name found in the list")
        }
    }

}