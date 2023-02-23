<template>
    <div class='w-full h-full grid grid-cols-pokerMain'>
        <div class='grid grid-rows-pokerTable'>
            <GameTable/>
            <MyCards/>
        </div>
        <UsersList/>
    </div>
</template>
<script>
import deck from '../assets/carddeck.js' 
import UsersList from '../components/UsersList.vue'
import MyCards from '../components/table/MyCards.vue'
import GameTable from '../components/table/GameTable.vue'
export default {
    name: "PokerTableView",
    components: {UsersList, GameTable, MyCards}

}
</script>
<style lang="">
    
</style>