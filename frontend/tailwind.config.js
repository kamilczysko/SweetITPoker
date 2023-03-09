/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {
    screens: {
      'sm': '640px',
      // => @media (min-width: 640px) { ... }

      'md': '768px',
      // => @media (min-width: 768px) { ... }

      'lg': '1024px',
      // => @media (min-width: 1024px) { ... }

      'xl': '1280px',
      // => @media (min-width: 1280px) { ... }

      '2xl': '1550px',
      // => @media (min-width: 1536px) { ... }
      'cutom': '1550px'
    },
  extend: {
    colors: {
      'bg1': '#FFDEE9',
      'bg2': '#B5FFFC',
      'bg3': '#C850C0'
    },
    gridTemplateColumns: {
      inputGrid: '1fr 3fr',
      pokerMain: '9fr 1fr',
      pokerMainSmaller: '9fr 2fr',
      pokerMainEvenSmaller: '9fr 3fr',
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
      'card-bg-2': "url('/src/assets/background2.jpg')",
      'card-bg-3': "url('/src/assets/background3.jpg')",
      'card-bg-1': "url('/src/assets/background.jpg')",
      'revers': "url('/src/assets/revers.png')"
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