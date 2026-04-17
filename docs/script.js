/* ============================================================
   yGuarder GUI — GitHub Pages JavaScript
   ============================================================ */

// === NAVBAR SCROLL EFFECT ===
const navbar = document.getElementById('navbar');
let lastScroll = 0;

window.addEventListener('scroll', () => {
    const currentScroll = window.pageYOffset;
    if (currentScroll > 30) {
        navbar.classList.add('scrolled');
    } else {
        navbar.classList.remove('scrolled');
    }
    lastScroll = currentScroll;
}, { passive: true });

// === MOBILE NAV TOGGLE ===
const navToggle = document.getElementById('navToggle');
const navLinks = document.querySelector('.nav-links');
const navActions = document.querySelector('.nav-actions');

if (navToggle) {
    navToggle.addEventListener('click', () => {
        const isOpen = navLinks.style.display === 'flex';
        if (isOpen) {
            navLinks.style.display = '';
            navActions.style.display = '';
            navToggle.classList.remove('open');
        } else {
            navLinks.style.cssText = 'display:flex; flex-direction:column; position:fixed; top:68px; left:0; right:0; background:rgba(10,10,15,0.98); backdrop-filter:blur(20px); padding:1rem 1.5rem 1.5rem; border-bottom:1px solid rgba(255,255,255,0.07); gap:0.25rem; z-index:999;';
            navActions.style.cssText = 'display:flex; flex-direction:column; position:fixed; top:calc(68px + var(--nav-links-height, 180px)); left:0; right:0; background:rgba(10,10,15,0.98); backdrop-filter:blur(20px); padding:1rem 1.5rem; border-bottom:1px solid rgba(255,255,255,0.07); z-index:999;';
            navToggle.classList.add('open');
        }
    });
}

// === QUICK START TABS ===
function switchTab(tab) {
    document.querySelectorAll('.qs-tab').forEach(t => t.classList.remove('active'));
    document.querySelectorAll('.qs-content').forEach(c => c.classList.remove('active'));

    const activeTab = document.getElementById('tab-' + tab);
    const activePanel = document.getElementById('panel-' + tab);
    if (activeTab) activeTab.classList.add('active');
    if (activePanel) activePanel.classList.add('active');
}

// === COPY BUTTON ===
function copyCode(btn) {
    let text = btn.getAttribute('data-text');

    if (!text) {
        // Find the code block in the same panel
        const panel = btn.closest('.code-panel');
        const codeEl = panel ? panel.querySelector('.code-block code') : null;
        text = codeEl ? codeEl.textContent : '';
    }

    if (text) {
        navigator.clipboard.writeText(text.trim()).then(() => {
            btn.textContent = '✓ Copied';
            btn.classList.add('copied');
            setTimeout(() => {
                btn.textContent = 'Copy';
                btn.classList.remove('copied');
            }, 2000);
        }).catch(() => {
            // Fallback
            const ta = document.createElement('textarea');
            ta.value = text;
            ta.style.position = 'fixed';
            ta.style.opacity = '0';
            document.body.appendChild(ta);
            ta.select();
            document.execCommand('copy');
            document.body.removeChild(ta);
            btn.textContent = '✓ Copied';
            btn.classList.add('copied');
            setTimeout(() => {
                btn.textContent = 'Copy';
                btn.classList.remove('copied');
            }, 2000);
        });
    }
}

// === INTERSECTION OBSERVER — ANIMATE ON SCROLL ===
const observerOptions = {
    threshold: 0.08,
    rootMargin: '0px 0px -40px 0px'
};

const observer = new IntersectionObserver((entries) => {
    entries.forEach((entry) => {
        if (entry.isIntersecting) {
            entry.target.classList.add('visible');
            observer.unobserve(entry.target);
        }
    });
}, observerOptions);

// Add animation classes and observe elements
document.addEventListener('DOMContentLoaded', () => {
    // Feature cards
    document.querySelectorAll('.feature-card').forEach((el, i) => {
        el.style.opacity = '0';
        el.style.transform = 'translateY(24px)';
        el.style.transition = `opacity 0.5s ease ${i * 0.07}s, transform 0.5s ease ${i * 0.07}s`;
        observer.observe(el);
    });

    // Step cards
    document.querySelectorAll('.step-card').forEach((el, i) => {
        el.style.opacity = '0';
        el.style.transform = 'translateY(20px)';
        el.style.transition = `opacity 0.5s ease ${i * 0.12}s, transform 0.5s ease ${i * 0.12}s`;
        observer.observe(el);
    });

    // DS features
    document.querySelectorAll('.ds-feature').forEach((el, i) => {
        el.style.opacity = '0';
        el.style.transform = 'translateX(-16px)';
        el.style.transition = `opacity 0.4s ease ${i * 0.1}s, transform 0.4s ease ${i * 0.1}s`;
        observer.observe(el);
    });

    // Code panels
    document.querySelectorAll('.ds-code-panel .code-panel').forEach((el, i) => {
        el.style.opacity = '0';
        el.style.transform = 'translateX(20px)';
        el.style.transition = `opacity 0.5s ease ${i * 0.15}s, transform 0.5s ease ${i * 0.15}s`;
        observer.observe(el);
    });

    // Arch components
    document.querySelectorAll('.arch-component').forEach((el, i) => {
        el.style.opacity = '0';
        el.style.transform = 'translateY(12px)';
        el.style.transition = `opacity 0.35s ease ${i * 0.06}s, transform 0.35s ease ${i * 0.06}s`;
        observer.observe(el);
    });
});

// Observer that adds 'visible' state
const visibilityObserver = new IntersectionObserver((entries) => {
    entries.forEach((entry) => {
        if (entry.isIntersecting) {
            entry.target.style.opacity = '1';
            entry.target.style.transform = 'translateY(0) translateX(0)';
            visibilityObserver.unobserve(entry.target);
        }
    });
}, { threshold: 0.08, rootMargin: '0px 0px -30px 0px' });

document.addEventListener('DOMContentLoaded', () => {
    setTimeout(() => {
        document.querySelectorAll(
            '.feature-card, .step-card, .ds-feature, .ds-code-panel .code-panel, .arch-component'
        ).forEach(el => visibilityObserver.observe(el));
    }, 100);
});

// === SMOOTH SCROLL FOR ANCHOR LINKS ===
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', (e) => {
        const target = document.querySelector(anchor.getAttribute('href'));
        if (target) {
            e.preventDefault();
            const offset = 80; // navbar height
            const top = target.getBoundingClientRect().top + window.pageYOffset - offset;
            window.scrollTo({ top, behavior: 'smooth' });

            // Close mobile nav if open
            if (navLinks && navLinks.style.display === 'flex') {
                navToggle.click();
            }
        }
    });
});

// === TYPING ANIMATION FOR CODE BLOCK (optional flair) ===
function animateCodeBlock() {
    const hero = document.querySelector('.mock-log-line--active');
    if (!hero) return;
    const text = hero.textContent;
    hero.textContent = '';
    let i = 0;
    const interval = setInterval(() => {
        hero.textContent += text[i];
        i++;
        if (i >= text.length) clearInterval(interval);
    }, 30);
}
setTimeout(animateCodeBlock, 1000);

// === ACTIVE NAV LINK ON SCROLL ===
const sections = document.querySelectorAll('section[id]');
const navLinkEls = document.querySelectorAll('.nav-links a');

window.addEventListener('scroll', () => {
    let current = '';
    sections.forEach(section => {
        const sectionTop = section.offsetTop - 100;
        if (window.pageYOffset >= sectionTop) {
            current = section.getAttribute('id');
        }
    });
    navLinkEls.forEach(link => {
        link.style.color = '';
        if (link.getAttribute('href') === `#${current}`) {
            link.style.color = 'var(--text-primary)';
        }
    });
}, { passive: true });
