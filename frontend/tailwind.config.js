/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {
    screens: {
      'xs': '0px',
      'sm': '640px',
      'md': '768px',
      'lg': '1024px',
      'xl': '1280px',
      '2xl': '1550px',
      'cutom': '1550px'
    },
    extend: {
      aspectRatio: {
        'card': '183/280'
      },
      colors: {
        'bg1': '#FFDEE9',
        'bg2': '#B5FFFC',
        'bg3': '#C850C0',
        'main-font': '#DDDDDD',
        'button-font': '#DDDDDD',
        'card-font': '#140F11',
      },
      gridTemplateColumns: {
        header: '5fr 1px 5fr',
        roomView: '13fr 2fr',
        roomView_md: '12fr 3fr'
      },
      fontFamily: {
        main: 'Lobster',
        secondary: ['Poppins']
      },
      backgroundImage: {
        'revers': "url('/src/assets/card/revers.png')",
        'front': "url('/src/assets/card/front.png')",
        'revers-other': "url('/src/assets/card/revers-other.png')",
        'front-other': "url('/src/assets/card/front-other.png')",
        'question': "url('/src/assets/card/questionmark.png')",
        'coffee': "url('/src/assets/card/coffee.png')",
        'background-first': "url('/src/assets/background/background.jpeg')",
        'background-second': "url('/src/assets/background/background.jpeg')",
        'background-third': "url('/src/assets/background/background.jpeg')",
      },
      backgroundSize: {
        'small': '90%'
      },
      boxShadow: {
        'round': '0 0 15px hotpink',
        'highlight': '0px 0px 15px hotpink',
        'highlight-more': '0px 0px 25px hotpink',
        'highlight-more': '0px 0px 10px hotpink'
      }
    },
  },
  plugins: [
    require('tailwind-scrollbar-hide')
  ],
}