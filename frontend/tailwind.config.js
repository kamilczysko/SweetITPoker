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
      'card': '2.7/4'
    },
    colors: {
      'bg1': '#FFDEE9',
      'bg2': '#B5FFFC',
      'bg3': '#C850C0'
    },
    gridTemplateColumns: {
      inputGrid: '1fr 3fr',
      pokerMain: '7fr 1fr',
      pokerMainSmaller: '7fr 2fr',
      pokerMainEvenSmaller: '7fr 3fr',
      deck: 'repeat(11, minmax(0, 1fr))',
      '3': 'repeat(3, minmax(0, 1fr))'
    },
    gridTemplateRows: {
      pokerTable: '9fr 3fr',
      nav: '1fr 8fr'
    },
    fontFamily: {
      main: 'Lobster',
      secondary: ['Poppins']
    },
    backgroundImage: {
      'revers': "url('/src/assets/revers.png')",
      'front': "url('/src/assets/front.png')"
    },
    backgroundSize: {
      'small': '90%'
    },
    boxShadow: {
      'round':'0 0 15px hotpink'
    }
  },
},
  plugins: [],
}