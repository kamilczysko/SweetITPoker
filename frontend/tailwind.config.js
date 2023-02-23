/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        'bg1': '#FFDEE9',
        'bg2': '#B5FFFC',
        'bg3': '#C850C0'
      },
      gridTemplateColumns: {
        inputGrid: '1fr 3fr',
        pokerMain: '7fr 1fr',
        deck: 'repeat(11, minmax(0, 1fr))',
        '3':  'repeat(3, minmax(0, 1fr))'
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
        'card-bg-1': "url('/src/assets/background.jpg')"
      },
      backgroundSize: {
        'small' : '90%'
      }
    },
  },
  plugins: [],
}